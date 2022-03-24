export interface IPersonHealth {
  time?: string;
  date?: string;
  dateObj?: Date;
  SpO2?: number;
  heartRate?: number;
}

export class PersonHealth implements IPersonHealth {
  constructor(public time?: string, public date?: string, public dateObj?: Date, public SpO2?: number, public heartRate?: number) {}
}
