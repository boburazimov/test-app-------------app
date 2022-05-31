export interface IUnit {
  id?: number;
  name?: string;
  description?: string | null;
}

export const defaultValue: Readonly<IUnit> = {};
