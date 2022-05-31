import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Social from './social';
import SocialDetail from './social-detail';
import SocialUpdate from './social-update';
import SocialDeleteDialog from './social-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SocialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SocialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SocialDetail} />
      <ErrorBoundaryRoute path={match.url} component={Social} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SocialDeleteDialog} />
  </>
);

export default Routes;
