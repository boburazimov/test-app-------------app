import { CatalogNameEnum } from 'app/shared/model/enumerations/catalog-name-enum.model';

export interface IGroup {
  id?: number;
  name?: string;
  info?: string | null;
  catalog?: CatalogNameEnum | null;
  parent?: IGroup | null;
}

export const defaultValue: Readonly<IGroup> = {};
