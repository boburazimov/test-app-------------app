import { IPriceType } from 'app/shared/model/price-type.model';
import { IGroup } from 'app/shared/model/group.model';

export interface IWarehouse {
  id?: number;
  name?: string;
  code?: string;
  isRetail?: boolean | null;
  isStockControl?: boolean | null;
  address?: string | null;
  info?: string | null;
  priceType?: IPriceType | null;
  group?: IGroup | null;
}

export const defaultValue: Readonly<IWarehouse> = {
  isRetail: false,
  isStockControl: false,
};
