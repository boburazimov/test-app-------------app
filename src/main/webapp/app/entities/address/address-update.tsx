import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRegion } from 'app/shared/model/region.model';
import { getEntities as getRegions } from 'app/entities/region/region.reducer';
import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { IDistrict } from 'app/shared/model/district.model';
import { getEntities as getDistricts } from 'app/entities/district/district.reducer';
import { IPartner } from 'app/shared/model/partner.model';
import { getEntities as getPartners } from 'app/entities/partner/partner.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';

export const AddressUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const regions = useAppSelector(state => state.region.entities);
  const cities = useAppSelector(state => state.city.entities);
  const districts = useAppSelector(state => state.district.entities);
  const partners = useAppSelector(state => state.partner.entities);
  const addressEntity = useAppSelector(state => state.address.entity);
  const loading = useAppSelector(state => state.address.loading);
  const updating = useAppSelector(state => state.address.updating);
  const updateSuccess = useAppSelector(state => state.address.updateSuccess);
  const handleClose = () => {
    props.history.push('/address');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRegions({}));
    dispatch(getCities({}));
    dispatch(getDistricts({}));
    dispatch(getPartners({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...addressEntity,
      ...values,
      region: regions.find(it => it.id.toString() === values.region.toString()),
      city: cities.find(it => it.id.toString() === values.city.toString()),
      district: districts.find(it => it.id.toString() === values.district.toString()),
      partner: partners.find(it => it.id.toString() === values.partner.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...addressEntity,
          region: addressEntity?.region?.id,
          city: addressEntity?.city?.id,
          district: addressEntity?.district?.id,
          partner: addressEntity?.partner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testApp.address.home.createOrEditLabel" data-cy="AddressCreateUpdateHeading">
            Create or edit a Address
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="address-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Street Adress"
                id="address-streetAdress"
                name="streetAdress"
                data-cy="streetAdress"
                type="text"
                validate={{
                  maxLength: { value: 200, message: 'This field cannot be longer than 200 characters.' },
                }}
              />
              <ValidatedField label="Longitude" id="address-longitude" name="longitude" data-cy="longitude" type="text" />
              <ValidatedField label="Latitude" id="address-latitude" name="latitude" data-cy="latitude" type="text" />
              <ValidatedField label="Zipcode" id="address-zipcode" name="zipcode" data-cy="zipcode" type="text" />
              <ValidatedField
                label="Info"
                id="address-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 200, message: 'This field cannot be longer than 200 characters.' },
                }}
              />
              <ValidatedField id="address-region" name="region" data-cy="region" label="Region" type="select">
                <option value="" key="0" />
                {regions
                  ? regions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="address-city" name="city" data-cy="city" label="City" type="select">
                <option value="" key="0" />
                {cities
                  ? cities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="address-district" name="district" data-cy="district" label="District" type="select">
                <option value="" key="0" />
                {districts
                  ? districts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="address-partner" name="partner" data-cy="partner" label="Partner" type="select">
                <option value="" key="0" />
                {partners
                  ? partners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/address" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AddressUpdate;
