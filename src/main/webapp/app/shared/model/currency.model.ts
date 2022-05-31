export interface ICurrency {
  id?: number;
  name?: string;
  symbolCode?: string;
}

export const defaultValue: Readonly<ICurrency> = {};
