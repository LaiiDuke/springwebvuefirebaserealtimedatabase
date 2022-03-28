<template>
  <div class="home row">
    <div class="col-md-12">
      <h1 class="display-4" v-text="$t('home.title')">Welcome, Chart APP!</h1>
      <p class="lead" v-text="$t('home.subtitle')">Homepage</p>

      <div>
        <div class="alert alert-success">
          <line-chart :label-display="this.listLabel" :datasets-prop="this.dataSets"></line-chart>
        </div>
        <b-table :items="this.dataSets"></b-table>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./person-health.component.ts"></script>
