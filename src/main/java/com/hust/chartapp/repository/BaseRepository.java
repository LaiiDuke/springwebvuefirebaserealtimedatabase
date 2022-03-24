package com.hust.chartapp.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentPath;
import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;
import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseRealtimeDatabaseConfigurationProperties;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpBadRequestException;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpNotFoundException;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpUnauthorizedException;
import com.hust.chartapp.domain.Product;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public abstract class BaseRepository<T, ID> {

    @Value("${firebase-realtime-database.authkey}")
    private String authkey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper firebaseObjectMapper;

    @Autowired
    private FirebaseRealtimeDatabaseConfigurationProperties firebaseRealtimeDatabaseConfigurationProperties;

    private final Class<T> firebaseDocumentClazz = (Class) (
        (ParameterizedType) this.getClass().getGenericSuperclass()
    ).getActualTypeArguments()[0];
    private final Class<ID> documentIdClazz = (Class) (
        (ParameterizedType) this.getClass().getGenericSuperclass()
    ).getActualTypeArguments()[1];
    private String documentPath;
    private final Field documentIdField;

    public BaseRepository() {
        if (this.firebaseDocumentClazz.isAnnotationPresent(FirebaseDocumentPath.class)) {
            this.documentPath = (this.firebaseDocumentClazz.getAnnotation(FirebaseDocumentPath.class)).value();
            if (!this.documentPath.isEmpty()) {
                if (!"/".equals(this.documentPath.substring(0, 1))) {
                    this.documentPath = "/" + this.documentPath;
                }

                this.documentIdField =
                    Arrays
                        .stream(this.firebaseDocumentClazz.getDeclaredFields())
                        .filter(field -> {
                            return field.isAnnotationPresent(FirebaseDocumentId.class);
                        })
                        .findFirst()
                        .orElseThrow(() -> {
                            return new RuntimeException("There is no @FirebaseDocumentId annotation!");
                        });
            } else {
                throw new RuntimeException("@FirebaseDocumentPath annotation's value is not set!");
            }
        } else {
            throw new RuntimeException("There is no @FirebaseDocumentPath annotation!");
        }
    }

    public List<T> getAll() {
        String url = this.generateUrlGetAll(authkey);
        ResponseEntity responseEntity = null;

        try {
            responseEntity = this.restTemplate.getForEntity(url, Object.class);
        } catch (HttpStatusCodeException var7) {
            this.handleException(var7);
        }
        List<T> listRsp = new ArrayList<>();
        LinkedHashMap<ID, Object> rsp = (LinkedHashMap<ID, Object>) responseEntity.getBody();
        assert rsp != null;
        rsp.forEach((k, v) -> {
            T t = initObj(v, k);
            listRsp.add(t);
        });

        return listRsp;
    }

    public T initObj(Object input, ID id) {
        T t = null;
        if (input instanceof LinkedHashMap) {
            t = new ObjectMapper().convertValue(input, this.firebaseDocumentClazz);
            this.setAnnotatedFieldValue(t, this.documentIdField, id);
        } else {
            try {
                t = new ObjectMapper().readValue((String) input, this.firebaseDocumentClazz);
                this.setAnnotatedFieldValue(t, this.documentIdField, id);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        this.doSthToObj(input, id, t);
        return t;
    }

    public abstract T doSthToObj(Object input, ID id, T obj);

    private String generateUrlGetAll(String authKey) {
        String url = this.firebaseRealtimeDatabaseConfigurationProperties.getDatabaseUrl();
        url = url + this.documentPath + ".json?auth=" + authKey;
        return url;
    }

    public T read(T obj) {
        String firebaseId = this.getAnnotatedFieldValue(obj, this.documentIdField);
        String url = this.generateUrl("read", authkey, firebaseId);
        ResponseEntity<T> responseEntity = null;

        try {
            responseEntity = this.restTemplate.getForEntity(url, this.firebaseDocumentClazz);
        } catch (HttpStatusCodeException var7) {
            this.handleException(var7);
        }

        if (responseEntity.getBody() == null) {
            throw new HttpNotFoundException("FirebaseDocumentId Not Found");
        } else {
            return responseEntity.getBody();
        }
    }

    /** @deprecated */
    @Deprecated
    public FirebaseSaveResponse save(T obj) {
        return this.saveWithRandomId(obj);
    }

    public FirebaseSaveResponse saveWithRandomId(T obj) {
        String firebaseId = this.getAnnotatedFieldValue(obj, this.documentIdField);
        String url = this.generateUrl("saveWithRandomId", authkey, firebaseId);
        HttpEntity<T> requestBody = null;

        try {
            requestBody = new HttpEntity(this.firebaseObjectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException var8) {
            throw new RuntimeException(var8);
        }

        ResponseEntity responseEntity = null;

        try {
            responseEntity = this.restTemplate.postForEntity(url, requestBody, FirebaseSaveResponse.class);
        } catch (HttpStatusCodeException var9) {
            this.handleException(var9);
        }

        return (FirebaseSaveResponse) responseEntity.getBody();
    }

    public FirebaseSaveResponse saveWithSpecificId(T obj) {
        String firebaseId = this.getAnnotatedFieldValue(obj, this.documentIdField);
        String url = "";

        try {
            this.read(obj);
        } catch (HttpNotFoundException var8) {
            url = this.generateUrl("saveWithSpecificId", authkey, firebaseId);
        }

        if (url.isEmpty()) {
            throw new HttpBadRequestException("FirebaseDocumentId Already Exists");
        } else {
            HttpEntity requestBody = null;

            try {
                requestBody = new HttpEntity(this.firebaseObjectMapper.writeValueAsString(obj));
            } catch (JsonProcessingException var7) {
                throw new RuntimeException(var7);
            }

            try {
                this.restTemplate.put(url, requestBody);
            } catch (HttpStatusCodeException var9) {
                this.handleException(var9);
            }

            FirebaseSaveResponse firebaseSaveResponse = new FirebaseSaveResponse();
            firebaseSaveResponse.setName(firebaseId);
            return firebaseSaveResponse;
        }
    }

    public void update(T obj) {
        String firebaseId = this.getAnnotatedFieldValue(obj, this.documentIdField);
        this.read(obj);
        String url = this.generateUrl("update", authkey, firebaseId);
        HttpEntity requestBody = null;

        try {
            requestBody = new HttpEntity(this.firebaseObjectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException var7) {
            throw new RuntimeException(var7);
        }

        try {
            this.restTemplate.put(url, requestBody);
        } catch (HttpStatusCodeException var8) {
            this.handleException(var8);
        }
    }

    public void delete(T obj) {
        String firebaseId = this.getAnnotatedFieldValue(obj, this.documentIdField);
        this.read(obj);
        String url = this.generateUrl("delete", authkey, firebaseId);

        try {
            this.restTemplate.delete(url);
        } catch (HttpStatusCodeException var6) {
            this.handleException(var6);
        }
    }

    private String getAnnotatedFieldValue(T obj, Field field) {
        String methodName = "";

        try {
            methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Method method = obj.getClass().getMethod(methodName);
            return (String) method.invoke(obj);
        } catch (NoSuchMethodException var6) {
            var6.printStackTrace();
            throw new RuntimeException("There is no " + methodName + " method in Class " + obj.getClass().getName());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException var7) {
            var7.printStackTrace();
            throw new RuntimeException(var7);
        }
    }

    private void setAnnotatedFieldValue(T obj, Field field, ID value) {
        String methodName = "";

        try {
            methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Method method = obj.getClass().getMethod(methodName, documentIdClazz);
            method.invoke(obj, value);
        } catch (NoSuchMethodException var6) {
            var6.printStackTrace();
            throw new RuntimeException("There is no " + methodName + " method in Class " + obj.getClass().getName());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException var7) {
            var7.printStackTrace();
            throw new RuntimeException(var7);
        }
    }

    private String generateUrl(String callerMethod, String authKey, String firebaseId) {
        if ((callerMethod.equals("read") || callerMethod.equals("update") || callerMethod.equals("delete")) && firebaseId.isEmpty()) {
            throw new RuntimeException("@FirebaseDocumentId annotation's value is not set!");
        } else if (authKey.isEmpty()) {
            throw new RuntimeException("@FirebaseUserAuthKey annotation's value is not set!");
        } else {
            String url = this.firebaseRealtimeDatabaseConfigurationProperties.getDatabaseUrl();
            if (!callerMethod.equals("read") && !callerMethod.equals("update") && !callerMethod.equals("delete")) {
                if (callerMethod.equals("saveWithRandomId")) {
                    url = url + this.documentPath + ".json?auth=" + authKey;
                } else if (callerMethod.equals("saveWithSpecificId")) {
                    url = url + this.documentPath + "/" + firebaseId + ".json?auth=" + authKey;
                }
            } else {
                url = url + this.documentPath + "/" + firebaseId + ".json?auth=" + authKey;
            }

            return url;
        }
    }

    private void handleException(HttpStatusCodeException var6) {
        if (var6.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new HttpBadRequestException(var6.getResponseBodyAsString());
        } else if (var6.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new HttpNotFoundException(var6.getResponseBodyAsString());
        } else if (var6.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new HttpUnauthorizedException(var6.getResponseBodyAsString());
        } else {
            throw new RuntimeException();
        }
    }
}
