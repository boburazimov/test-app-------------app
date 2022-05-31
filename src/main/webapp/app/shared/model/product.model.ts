import { IBrand } from 'app/shared/model/brand.model';
import { IAttachment } from 'app/shared/model/attachment.model';
import { ICategory } from 'app/shared/model/category.model';
import { IGroup } from 'app/shared/model/group.model';
import { IUnit } from 'app/shared/model/unit.model';
import { GeneralStatusEnum } from 'app/shared/model/enumerations/general-status-enum.model';

export interface IProduct {
  id?: number;
  name?: string;
  description?: string | null;
  vendorCode?: string | null;
  code?: string;
  top?: boolean | null;
  vatRate?: number | null;
  madeIn?: string | null;
  info?: string | null;
  status?: GeneralStatusEnum | null;
  brand?: IBrand | null;
  photo?: IAttachment | null;
  category?: ICategory | null;
  group?: IGroup | null;
  unit?: IUnit | null;
}

export const defaultValue: Readonly<IProduct> = {
  top: false,
};
