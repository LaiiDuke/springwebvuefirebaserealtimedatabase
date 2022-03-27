import Component from 'vue-class-component';
import Vue from 'vue';
import axios, { AxiosPromise } from 'axios';

@Component
export default class RoomEnvironmentsService extends Vue {
  public findAll(): AxiosPromise<any> {
    return axios.get('api/v1/room-environments/all');
  }
  public latest(): AxiosPromise<any> {
    return axios.get('api/v1/room-environments/latest');
  }
}
