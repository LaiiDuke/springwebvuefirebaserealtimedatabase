import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import LineChart from '@/components/LineChart.vue';
import HomeService from '@/core/home/home.service';
import { PersonHealth } from '@/shared/model/person-health.model';
import PersonHealthService from '@/components/person-health/person-health.service';
import { PersonTemperature } from '@/shared/model/person-temperature.model';
import { RoomEnvironments } from '@/shared/model/room-environments.model';
import PersonTemperatureService from '@/components/person-temperature/person-temperature.service';
import RoomEnvironmentsService from '@/components/room-environments/room-environments.service';

@Component({
  components: {
    LineChart,
  },
})
export default class Home extends Vue {
  personHealth = new PersonHealth();
  personTemperature = new PersonTemperature();
  roomEnvironment = new RoomEnvironments();

  @Inject('loginService')
  private loginService: () => LoginService;

  @Inject('personHealthService')
  private personHealthService: () => PersonHealthService;

  @Inject('personTemperatureService')
  private personTemperatureService: () => PersonTemperatureService;

  @Inject('roomEnvironmentsService')
  private roomEnvironmentsService: () => RoomEnvironmentsService;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.personHealthService()
      .latest()
      .then(response => {
        this.personHealth = response.data;
      });
    this.personTemperatureService()
      .latest()
      .then(response => {
        this.personTemperature = response.data;
      });
    this.roomEnvironmentsService()
      .latest()
      .then(response => {
        this.roomEnvironment = response.data;
      });
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account?.login ?? '';
  }
}
