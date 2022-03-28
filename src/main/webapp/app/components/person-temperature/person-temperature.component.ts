import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LineChart from '@/components/LineChart.vue';
import PersonTemperatureService from '@/components/person-temperature/person-temperature.service';

@Component({
  components: {
    LineChart,
  },
})
export default class PersonTemperatureComponent extends Vue {
  listData = [];
  listTemperature = [];
  dataSets = [];
  listLabel = [];

  @Inject('personTemperatureService')
  private personTemperatureService: () => PersonTemperatureService;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.personTemperatureService()
      .findAll()
      .then(response => {
        this.listData = response.data;
        response.data.forEach(x => {
          this.listTemperature.push(x.tempObject);
          this.listLabel.push(x.time + '-' + x.date);
        });
        this.dataSets.push({
          label: 'Temperature',
          type: 'line',
          data: this.listTemperature,
          fill: false,
          borderColor: '#2554FF',
          backgroundColor: '#2554FF',
          borderWidth: 1,
        });
      });
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account?.login ?? '';
  }
}
