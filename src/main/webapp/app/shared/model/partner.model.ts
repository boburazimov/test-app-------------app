import { IGroup } from 'app/shared/model/group.model';
import { ISocial } from 'app/shared/model/social.model';
import { GenderEnum } from 'app/shared/model/enumerations/gender-enum.model';
import { PartnerTypeEnum } from 'app/shared/model/enumerations/partner-type-enum.model';

export interface IPartner {
  id?: number;
  phonenumber?: string;
  code?: string;
  firstName?: string | null;
  lastName?: string | null;
  gender?: GenderEnum | null;
  age?: number | null;
  isBlocked?: boolean | null;
  partnerType?: PartnerTypeEnum | null;
  inn?: number | null;
  pinfl?: number | null;
  group?: IGroup | null;
  social?: ISocial | null;
}

export const defaultValue: Readonly<IPartner> = {
  isBlocked: false,
};
