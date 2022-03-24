import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import LineChart from '@/components/LineChart.vue';
import HomeService from '@/core/home/home.service';
import { PersonHealth } from '@/shared/model/person-health.model';

@Component({
  components: {
    LineChart,
  },
})
export default class Home extends Vue {
  personHealth = new PersonHealth();

  @Inject('loginService')
  private loginService: () => LoginService;

  @Inject('homeService')
  private homeService: () => HomeService;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.homeService()
      .findAll()
      .then(response => {
        this.personHealth = response.data;
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
