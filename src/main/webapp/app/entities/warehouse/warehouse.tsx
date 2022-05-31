import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWarehouse } from 'app/shared/model/warehouse.model';
import { getEntities } from './warehouse.reducer';

export const Warehouse = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const warehouseList = useAppSelector(state => state.warehouse.entities);
  const loading = useAppSelector(state => state.warehouse.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="warehouse-heading" data-cy="WarehouseHeading">
        Warehouses
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/warehouse/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Warehouse
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {warehouseList && warehouseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Code</th>
                <th>Is Retail</th>
                <th>Is Stock Control</th>
                <th>Address</th>
                <th>Info</th>
                <th>Price Type</th>
                <th>Group</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {warehouseList.map((warehouse, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/warehouse/${warehouse.id}`} color="link" size="sm">
                      {warehouse.id}
                    </Button>
                  </td>
                  <td>{warehouse.name}</td>
                  <td>{warehouse.code}</td>
                  <td>{warehouse.isRetail ? 'true' : 'false'}</td>
                  <td>{warehouse.isStockControl ? 'true' : 'false'}</td>
                  <td>{warehouse.address}</td>
                  <td>{warehouse.info}</td>
                  <td>{warehouse.priceType ? <Link to={`/price-type/${warehouse.priceType.id}`}>{warehouse.priceType.id}</Link> : ''}</td>
                  <td>{warehouse.group ? <Link to={`/group/${warehouse.group.id}`}>{warehouse.group.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/warehouse/${warehouse.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/warehouse/${warehouse.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/warehouse/${warehouse.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Warehouses found</div>
        )}
      </div>
    </div>
  );
};

export default Warehouse;
