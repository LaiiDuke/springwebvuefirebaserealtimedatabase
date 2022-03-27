import Component from 'vue-class-component';
import Vue from 'vue';
import axios, { AxiosPromise } from 'axios';

@Component
export default class HomeService extends Vue {
  public changeLevel(name: string, configuredLevel: string): AxiosPromise<any> {
    return axios.post(`management/loggers/${name}`, { configuredLevel });
  }
}
