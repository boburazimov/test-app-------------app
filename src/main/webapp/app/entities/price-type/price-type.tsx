import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPriceType } from 'app/shared/model/price-type.model';
import { getEntities } from './price-type.reducer';

export const PriceType = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const priceTypeList = useAppSelector(state => state.priceType.entities);
  const loading = useAppSelector(state => state.priceType.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="price-type-heading" data-cy="PriceTypeHeading">
        Price Types
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/price-type/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Price Type
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {priceTypeList && priceTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Include Vat</th>
                <th>Status</th>
                <th>Info</th>
                <th>Group</th>
                <th>Currency</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {priceTypeList.map((priceType, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/price-type/${priceType.id}`} color="link" size="sm">
                      {priceType.id}
                    </Button>
                  </td>
                  <td>{priceType.name}</td>
                  <td>{priceType.includeVat ? 'true' : 'false'}</td>
                  <td>{priceType.status}</td>
                  <td>{priceType.info}</td>
                  <td>{priceType.group ? <Link to={`/group/${priceType.group.id}`}>{priceType.group.id}</Link> : ''}</td>
                  <td>{priceType.currency ? <Link to={`/currency/${priceType.currency.id}`}>{priceType.currency.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/price-type/${priceType.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/price-type/${priceType.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/price-type/${priceType.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Price Types found</div>
        )}
      </div>
    </div>
  );
};

export default PriceType;
