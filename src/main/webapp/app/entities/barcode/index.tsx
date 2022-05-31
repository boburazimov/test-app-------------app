import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Barcode from './barcode';
import BarcodeDetail from './barcode-detail';
import BarcodeUpdate from './barcode-update';
import BarcodeDeleteDialog from './barcode-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BarcodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BarcodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BarcodeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Barcode} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BarcodeDeleteDialog} />
  </>
);

export default Routes;
