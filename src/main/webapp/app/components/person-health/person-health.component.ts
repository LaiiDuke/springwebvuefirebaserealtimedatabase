import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import LineChart from '@/components/LineChart.vue';
import HomeService from '@/core/home/home.service';
import { PersonHealth } from '@/shared/model/person-health.model';
import PersonHealthService from '@/components/person-health/person-health.service';

@Component({
  components: {
    LineChart,
  },
})
export default class PersonHealthComponent extends Vue {
  listData = [];
  listDataHeartRate = [];
  listDataSpO2 = [];
  dataSets = [];
  listLabel = [];

  @Inject('personHealthService')
  private personHealthService: () => PersonHealthService;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.personHealthService()
      .findAll()
      .then(response => {
        this.listData = response.data;
        response.data.forEach(x => {
          this.listDataHeartRate.push(x.heartRate);
          this.listLabel.push(x.time + '-' + x.date);
          this.listDataSpO2.push(x.SpO2);
        });
        this.dataSets.push({
          label: 'Heart Rate',
          type: 'line',
          data: this.listDataHeartRate,
          fill: false,
          borderColor: '#2554FF',
          backgroundColor: '#2554FF',
          borderWidth: 1,
        });
        this.dataSets.push({
          label: 'SpO2',
          type: 'bar',
          data: this.listDataSpO2,
          fill: false,
          borderColor: '#ffcece',
          backgroundColor: '#ffdada',
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
