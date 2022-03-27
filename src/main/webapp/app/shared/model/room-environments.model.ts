export interface IRoomEnvironments {
  time?: string;
  date?: string;
  dateObj?: Date;
  roomTemp?: number;
  roomHum?: number;
}

export class RoomEnvironments implements IRoomEnvironments {
  constructor(public time?: string, public date?: string, public dateObj?: Date, public roomTemp?: number, public roomHum?: number) {}
}
