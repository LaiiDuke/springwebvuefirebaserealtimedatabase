import Component from 'vue-class-component';
import Vue from 'vue';
import axios, { AxiosPromise } from 'axios';

@Component
export default class HomeService extends Vue {
  public changeLevel(name: string, configuredLevel: string): AxiosPromise<any> {
    return axios.post(`management/loggers/${name}`, { configuredLevel });
  }

  public findAll(): AxiosPromise<any> {
    return axios.get('api/v1/person-health/latest');
  }
  public latest(): AxiosPromise<any> {
    return axios.get('api/v1/person-health/latest');
  }
}
