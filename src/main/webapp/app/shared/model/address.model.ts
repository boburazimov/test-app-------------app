import { IRegion } from 'app/shared/model/region.model';
import { ICity } from 'app/shared/model/city.model';
import { IDistrict } from 'app/shared/model/district.model';
import { IPartner } from 'app/shared/model/partner.model';

export interface IAddress {
  id?: number;
  streetAdress?: string | null;
  longitude?: string | null;
  latitude?: string | null;
  zipcode?: string | null;
  info?: string | null;
  region?: IRegion | null;
  city?: ICity | null;
  district?: IDistrict | null;
  partner?: IPartner | null;
}

export const defaultValue: Readonly<IAddress> = {};
