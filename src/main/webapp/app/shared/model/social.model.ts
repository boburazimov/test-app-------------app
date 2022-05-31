export interface ISocial {
  id?: number;
  name?: string;
  info?: string | null;
}

export const defaultValue: Readonly<ISocial> = {};
