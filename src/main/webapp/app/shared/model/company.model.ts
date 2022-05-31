export interface ICompany {
  id?: number;
  name?: string;
  info?: string | null;
}

export const defaultValue: Readonly<ICompany> = {};
