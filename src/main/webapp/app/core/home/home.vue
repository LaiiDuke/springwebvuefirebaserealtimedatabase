<template>
  <div class="home row">
    <div class="col-md-3">
      <span class="hipster img-fluid rounded"></span>
    </div>
    <div class="col-md-9">
      <h1 class="display-4" v-text="$t('home.title')">Welcome, Chart APP!</h1>
      <p class="lead" v-text="$t('home.subtitle')">Homepage</p>

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username" v-text="$t('home.logged.message', { username: username })">You are logged in as user "{{ username }}"</span>
          <!--          <line-chart></line-chart>-->
        </div>

        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="$t('global.messages.info.authenticated.prefix')">If you want to </span>
          <a class="alert-link" v-on:click="openLogin()" v-text="$t('global.messages.info.authenticated.link')">sign in</a>
        </div>
      </div>
      <div class="row">
        <div class="alert col-md-4 alert-info">
          <h4>Person Health</h4>
          <p>Time: {{ personHealth.time }}</p>
          <p>Heart Rate: {{ personHealth.heartRate }}</p>
          <p>SpO2: {{ personHealth.SpO2 }}</p>
          <router-link to="/person-health" class="btn btn-danger">Detail</router-link>
        </div>
        <div class="alert col-md-4 alert-success">
          <h4>Person Temperature</h4>
          <p>Temperature:</p>
        </div>
        <div class="alert col-md-4 alert-secondary">
          <h4>Environment Temperature</h4>
          <p>Time:</p>
          <p>Temperature:</p>
          <p>Humidity:</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
