import * as React from "react";

export interface UploadWrapperProps {
  type?: string;
  wrapperProps?: object;
  wrapperStyle?: string;
  limit?: number;
}

export default class UploadWrapper<T = any> extends React.Component<T> {}
