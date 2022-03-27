import Component from 'vue-class-component';
import Vue from 'vue';
import axios, { AxiosPromise } from 'axios';

@Component
export default class PersonHealthService extends Vue {
  public findAll(): AxiosPromise<any> {
    return axios.get('api/v1/person-health/all');
  }
  public latest(): AxiosPromise<any> {
    return axios.get('api/v1/person-health/latest');
  }
}
