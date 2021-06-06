import * as React from "react";

export interface DropzoneAreaProps {
  BannerComponent?: any;
  PreviewComponent?: any;
  bannerStyle?: string;
  bannerText?: string;
  inside?: boolean;
  limit?: number;
  preview?: boolean;
  snackBarMessage?: object;
  type?: string;
  uploadIcon?: string;
  wrapperProps?: object;
  wrapperStyle?: string;
}

export default class DropzoneArea<T = any> extends React.Component<T> {}
