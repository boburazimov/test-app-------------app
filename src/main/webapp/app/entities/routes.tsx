import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Company from './company';
import Position from './position';
import Partner from './partner';
import Group from './group';
import Address from './address';
import District from './district';
import City from './city';
import Region from './region';
import Social from './social';
import Country from './country';
import Warehouse from './warehouse';
import PriceType from './price-type';
import Currency from './currency';
import Attachment from './attachment';
import Product from './product';
import Brand from './brand';
import Category from './category';
import Unit from './unit';
import Barcode from './barcode';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
        <ErrorBoundaryRoute path={`${match.url}position`} component={Position} />
        <ErrorBoundaryRoute path={`${match.url}partner`} component={Partner} />
        <ErrorBoundaryRoute path={`${match.url}group`} component={Group} />
        <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
        <ErrorBoundaryRoute path={`${match.url}district`} component={District} />
        <ErrorBoundaryRoute path={`${match.url}city`} component={City} />
        <ErrorBoundaryRoute path={`${match.url}region`} component={Region} />
        <ErrorBoundaryRoute path={`${match.url}social`} component={Social} />
        <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
        <ErrorBoundaryRoute path={`${match.url}warehouse`} component={Warehouse} />
        <ErrorBoundaryRoute path={`${match.url}price-type`} component={PriceType} />
        <ErrorBoundaryRoute path={`${match.url}currency`} component={Currency} />
        <ErrorBoundaryRoute path={`${match.url}attachment`} component={Attachment} />
        <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
        <ErrorBoundaryRoute path={`${match.url}brand`} component={Brand} />
        <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
        <ErrorBoundaryRoute path={`${match.url}unit`} component={Unit} />
        <ErrorBoundaryRoute path={`${match.url}barcode`} component={Barcode} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
