import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './price-type.reducer';

export const PriceTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const priceTypeEntity = useAppSelector(state => state.priceType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="priceTypeDetailsHeading">PriceType</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{priceTypeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{priceTypeEntity.name}</dd>
          <dt>
            <span id="includeVat">Include Vat</span>
          </dt>
          <dd>{priceTypeEntity.includeVat ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{priceTypeEntity.status}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{priceTypeEntity.info}</dd>
          <dt>Group</dt>
          <dd>{priceTypeEntity.group ? priceTypeEntity.group.id : ''}</dd>
          <dt>Currency</dt>
          <dd>{priceTypeEntity.currency ? priceTypeEntity.currency.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/price-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/price-type/${priceTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PriceTypeDetail;
