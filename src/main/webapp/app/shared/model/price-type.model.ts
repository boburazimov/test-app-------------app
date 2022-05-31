import { IGroup } from 'app/shared/model/group.model';
import { ICurrency } from 'app/shared/model/currency.model';
import { GeneralStatusEnum } from 'app/shared/model/enumerations/general-status-enum.model';

export interface IPriceType {
  id?: number;
  name?: string;
  includeVat?: boolean | null;
  status?: GeneralStatusEnum | null;
  info?: string | null;
  group?: IGroup | null;
  currency?: ICurrency | null;
}

export const defaultValue: Readonly<IPriceType> = {
  includeVat: false,
};
