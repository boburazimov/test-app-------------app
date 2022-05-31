import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './warehouse.reducer';

export const WarehouseDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const warehouseEntity = useAppSelector(state => state.warehouse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="warehouseDetailsHeading">Warehouse</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{warehouseEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{warehouseEntity.name}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{warehouseEntity.code}</dd>
          <dt>
            <span id="isRetail">Is Retail</span>
          </dt>
          <dd>{warehouseEntity.isRetail ? 'true' : 'false'}</dd>
          <dt>
            <span id="isStockControl">Is Stock Control</span>
          </dt>
          <dd>{warehouseEntity.isStockControl ? 'true' : 'false'}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{warehouseEntity.address}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{warehouseEntity.info}</dd>
          <dt>Price Type</dt>
          <dd>{warehouseEntity.priceType ? warehouseEntity.priceType.id : ''}</dd>
          <dt>Group</dt>
          <dd>{warehouseEntity.group ? warehouseEntity.group.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/warehouse" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/warehouse/${warehouseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WarehouseDetail;
