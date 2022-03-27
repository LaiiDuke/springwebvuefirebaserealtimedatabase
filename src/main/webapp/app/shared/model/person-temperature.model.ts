export interface IPersonTemperature {
  time?: string;
  date?: string;
  dateObj?: Date;
  tempObject?: number;
}

export class PersonTemperature implements IPersonTemperature {
  constructor(public time?: string, public date?: string, public dateObj?: Date, public tempObject?: number) {}
}
