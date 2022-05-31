export interface IBrand {
  id?: number;
  name?: string;
  parent?: IBrand | null;
}

export const defaultValue: Readonly<IBrand> = {};
