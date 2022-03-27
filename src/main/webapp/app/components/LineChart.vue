<script>
import { Line } from 'vue-chartjs';

export default {
  extends: Line,
  props: {
    labelDisplay: {
      type: Array,
      default: () => [],
    },
    dataDisplay: {
      type: Array,
      default: () => [],
    },
    datasetsProp: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      chartData: {
        labels: this.labelDisplay,
        datasets: this.datasetsProp,
      },
      options: {
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
              gridLines: {
                display: true,
              },
            },
          ],
          xAxes: [
            {
              gridLines: {
                display: false,
              },
            },
          ],
        },
        legend: {
          display: true,
        },
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  mounted() {
    this.renderChart(this.chartData, this.options);
  },
  watch: {
    // whenever question changes, this function will run
    datasetsProp(newList, oldList) {
      this.renderChart(this.chartData, this.options);
    },
  },
};
</script>
