import { IGroup } from 'app/shared/model/group.model';

export interface IPosition {
  id?: number;
  name?: string;
  info?: string | null;
  group?: IGroup | null;
}

export const defaultValue: Readonly<IPosition> = {};
