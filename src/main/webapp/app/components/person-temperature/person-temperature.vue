<template>
  <div class="home row">
    <div class="col-md-12">
      <h1 class="display-4" v-text="$t('home.title')">Welcome, Chart APP!</h1>
      <p class="lead" v-text="$t('home.subtitle')">Homepage</p>

      <div>
        <div class="alert alert-warning">
          <line-chart :label-display="this.listLabel" :datasets-prop="this.dataSets"></line-chart>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./person-temperature.component.ts"></script>
