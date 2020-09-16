import classNames from 'classnames';
import * as React from 'react';

import { set } from '../../util/FunctionalUtils/FunctionalUtils';
import { ListPresenterComponent } from '../Form/Context';
import { Form } from '../Form/Form';

import styles from './Form.module.css';

interface IFormPublicProps<T> {
  initialValues?: Partial<T>;
  formUnderKey?: string | string[];
  hideSubmit?: boolean;
}

interface IForm<T> extends IFormPublicProps<T> {}

const processOnSubmit = <T, R>(onSubmit: (data: R) => Promise<R>, formUnderKey?: DeepKeyOf<R>) =>
  (request: T): Promise<R> => {
    if (formUnderKey != null) {
      const body = {};
      // Assuming type safety from above, not checking it here
      set(body, formUnderKey as any, request);
      return onSubmit(body as R);
    }

    return onSubmit(request as unknown as R);
  };

export class FiltersForm<T> extends React.PureComponent<IForm<T>> {
  public render() {
    const { children, formUnderKey, hideSubmit, initialValues } = this.props;

    return (
      <ListPresenterComponent>
        {
          (ctx) => (
            <Form
              form={ctx!.conceptName}
              className={classNames(styles.Form, 'dslFiltersForm')}
              initialValues={initialValues as any}
              onSubmit={processOnSubmit(ctx!.onSubmit, formUnderKey)}
              disableSubmit={ctx!.isExporting}
              hideButtons={hideSubmit}
              submitButtonText={ctx!.isLoading ? 'Submitting...' : (ctx!.isExporting ? 'Exporting...' : 'Filter')}
            >
              {children}
            </Form>
          )
        }
      </ListPresenterComponent>
    );
  }
}
