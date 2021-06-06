import * as React from "react";

export interface FıleButtonProps {
  ButtonComponent?: any;
  fabIcon?: string;
  fabProps?: object;
  fabText?: string;
  inputProps?: object;
}

export default class FıleButton<T = any> extends React.Component<T> {}
