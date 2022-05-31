export interface ICategory {
  id?: number;
  name?: string;
  info?: string | null;
  parent?: ICategory | null;
}

export const defaultValue: Readonly<ICategory> = {};
