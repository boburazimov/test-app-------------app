import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">Address</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="streetAdress">Street Adress</span>
          </dt>
          <dd>{addressEntity.streetAdress}</dd>
          <dt>
            <span id="longitude">Longitude</span>
          </dt>
          <dd>{addressEntity.longitude}</dd>
          <dt>
            <span id="latitude">Latitude</span>
          </dt>
          <dd>{addressEntity.latitude}</dd>
          <dt>
            <span id="zipcode">Zipcode</span>
          </dt>
          <dd>{addressEntity.zipcode}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{addressEntity.info}</dd>
          <dt>Region</dt>
          <dd>{addressEntity.region ? addressEntity.region.id : ''}</dd>
          <dt>City</dt>
          <dd>{addressEntity.city ? addressEntity.city.id : ''}</dd>
          <dt>District</dt>
          <dd>{addressEntity.district ? addressEntity.district.id : ''}</dd>
          <dt>Partner</dt>
          <dd>{addressEntity.partner ? addressEntity.partner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
