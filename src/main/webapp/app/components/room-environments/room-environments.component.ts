import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LineChart from '@/components/LineChart.vue';
import RoomEnvironmentsService from '@/components/room-environments/room-environments.service';

@Component({
  components: {
    LineChart,
  },
})
export default class RoomEnvironmentsComponent extends Vue {
  listData = [];
  listTemp = [];
  listHum = [];
  dataSets = [];
  listLabel = [];

  @Inject('roomEnvironmentsService')
  private roomEnvironmentsService: () => RoomEnvironmentsService;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.roomEnvironmentsService()
      .findAll()
      .then(response => {
        this.listData = response.data;
        response.data.forEach(x => {
          this.listTemp.push(x.roomTemp);
          this.listLabel.push(x.time + '-' + x.date);
          this.listHum.push(x.roomHum);
        });
        this.dataSets.push({
          label: 'Temperature',
          type: 'line',
          data: this.listTemp,
          fill: false,
          borderColor: '#34fd00',
          backgroundColor: '#34fd00',
          borderWidth: 1,
        });
        this.dataSets.push({
          label: 'Humidity',
          type: 'bar',
          data: this.listHum,
          fill: false,
          borderColor: '#ff4040',
          backgroundColor: '#ff4040',
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
