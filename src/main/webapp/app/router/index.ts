import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);
import Router, { RouteConfig } from 'vue-router';

const Home = () => import('@/core/home/home.vue');
const Error = () => import('@/core/error/error.vue');
const PersonHealth = () => import('@/components/person-health/person-health.vue');
const PersonTemperature = () => import('@/components/person-temperature/person-temperature.vue');
const RoomEnvironments = () => import('@/components/room-environments/room-environments.vue');
import admin from '@/router/admin';
import entities from '@/router/entities';
import pages from '@/router/pages';

Vue.use(Router);

// prettier-ignore
const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/person-health',
      name: 'PersonHealth',
      component: PersonHealth,
    },
    {
      path: '/person-temperature',
      name: 'PersonTemperature',
      component: PersonTemperature,
    },
    {
      path: '/room-environments',
      name: 'RoomEnvironment',
      component: RoomEnvironments,
    },
    ...admin,
    entities,
    ...pages
  ]
});

export default router;
