<template>
  <div class="home row">
    <div class="col-md-12">
      <h1 class="display-4" v-text="$t('home.title')">Welcome, Chart APP!</h1>
      <p class="lead" v-text="$t('home.subtitle')">Homepage</p>

      <div>
        <div class="alert alert-warning">
          <line-chart :label-display="this.listLabel" :datasets-prop="this.dataSets"></line-chart>
        </div>
        <hr />
        <div>
          <h2>Temperature</h2>
          <br />
          <table class="table table-striped table-bordered">
            <thead>
              <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Temperature</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in this.listData" :key="item.dateObj">
                <td>{{ item.date }}</td>
                <td>{{ item.time }}</td>
                <td>{{ item.tempObject }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./person-temperature.component.ts"></script>
