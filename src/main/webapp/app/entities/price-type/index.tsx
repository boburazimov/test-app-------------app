import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PriceType from './price-type';
import PriceTypeDetail from './price-type-detail';
import PriceTypeUpdate from './price-type-update';
import PriceTypeDeleteDialog from './price-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PriceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PriceTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PriceTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={PriceType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PriceTypeDeleteDialog} />
  </>
);

export default Routes;
