import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGroup } from 'app/shared/model/group.model';
import { getEntities as getGroups } from 'app/entities/group/group.reducer';
import { ICurrency } from 'app/shared/model/currency.model';
import { getEntities as getCurrencies } from 'app/entities/currency/currency.reducer';
import { IPriceType } from 'app/shared/model/price-type.model';
import { GeneralStatusEnum } from 'app/shared/model/enumerations/general-status-enum.model';
import { getEntity, updateEntity, createEntity, reset } from './price-type.reducer';

export const PriceTypeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const groups = useAppSelector(state => state.group.entities);
  const currencies = useAppSelector(state => state.currency.entities);
  const priceTypeEntity = useAppSelector(state => state.priceType.entity);
  const loading = useAppSelector(state => state.priceType.loading);
  const updating = useAppSelector(state => state.priceType.updating);
  const updateSuccess = useAppSelector(state => state.priceType.updateSuccess);
  const generalStatusEnumValues = Object.keys(GeneralStatusEnum);
  const handleClose = () => {
    props.history.push('/price-type');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getGroups({}));
    dispatch(getCurrencies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...priceTypeEntity,
      ...values,
      group: groups.find(it => it.id.toString() === values.group.toString()),
      currency: currencies.find(it => it.id.toString() === values.currency.toString()),
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
          status: 'ACTIVE',
          ...priceTypeEntity,
          group: priceTypeEntity?.group?.id,
          currency: priceTypeEntity?.currency?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testApp.priceType.home.createOrEditLabel" data-cy="PriceTypeCreateUpdateHeading">
            Create or edit a PriceType
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="price-type-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="price-type-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 40, message: 'This field cannot be longer than 40 characters.' },
                }}
              />
              <ValidatedField label="Include Vat" id="price-type-includeVat" name="includeVat" data-cy="includeVat" check type="checkbox" />
              <ValidatedField label="Status" id="price-type-status" name="status" data-cy="status" type="select">
                {generalStatusEnumValues.map(generalStatusEnum => (
                  <option value={generalStatusEnum} key={generalStatusEnum}>
                    {generalStatusEnum}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Info"
                id="price-type-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 200, message: 'This field cannot be longer than 200 characters.' },
                }}
              />
              <ValidatedField id="price-type-group" name="group" data-cy="group" label="Group" type="select">
                <option value="" key="0" />
                {groups
                  ? groups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="price-type-currency" name="currency" data-cy="currency" label="Currency" type="select">
                <option value="" key="0" />
                {currencies
                  ? currencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/price-type" replace color="info">
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

export default PriceTypeUpdate;
