import * as React from "react";

export interface DropzoneCardProps {
  BannerComponent?: any;
  bannerStyle?: string;
  bannerText?: string;
  snackBarMessage?: object;
  type?: string;
  uploadIcon?: string;
  wrapperProps?: object;
  wrapperStyle?: string;
}

export default class DropzoneCard<T = any> extends React.Component<T> {}
