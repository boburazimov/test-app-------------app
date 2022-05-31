import company from 'app/entities/company/company.reducer';
import position from 'app/entities/position/position.reducer';
import partner from 'app/entities/partner/partner.reducer';
import group from 'app/entities/group/group.reducer';
import address from 'app/entities/address/address.reducer';
import district from 'app/entities/district/district.reducer';
import city from 'app/entities/city/city.reducer';
import region from 'app/entities/region/region.reducer';
import social from 'app/entities/social/social.reducer';
import country from 'app/entities/country/country.reducer';
import warehouse from 'app/entities/warehouse/warehouse.reducer';
import priceType from 'app/entities/price-type/price-type.reducer';
import currency from 'app/entities/currency/currency.reducer';
import attachment from 'app/entities/attachment/attachment.reducer';
import product from 'app/entities/product/product.reducer';
import brand from 'app/entities/brand/brand.reducer';
import category from 'app/entities/category/category.reducer';
import unit from 'app/entities/unit/unit.reducer';
import barcode from 'app/entities/barcode/barcode.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  company,
  position,
  partner,
  group,
  address,
  district,
  city,
  region,
  social,
  country,
  warehouse,
  priceType,
  currency,
  attachment,
  product,
  brand,
  category,
  unit,
  barcode,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
