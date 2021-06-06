# @devloops/material-ui-dropzone

>

[![NPM](https://img.shields.io/npm/v/@devloops/material-ui-dropzone.svg)](https://www.npmjs.com/package/@devloops/material-ui-dropzone)
[![JavaScript Style Guide](https://img.shields.io/badge/code_style-standard-brightgreen.svg)](https://standardjs.com)

## Install

```bash
npm install --save @devloops/material-ui-dropzone
#or
yarn add @devloops/material-ui-dropzone
```

## DropzoneArea Usage

```jsx
import React, { Component } from "react";

import { DropzoneArea } from "@devloops/material-ui-dropzone";

const DropzoneAreaExample = () => {
  return <DropzoneArea BannerComponent={DefaultBannerComponent /* or Default Comp*/} />;
};

const DefaultBannerComponent = ({ uploadIcon, bannerStyle, openFileDialog, bannerText }) => {
  const styles = makeStyles(DropzoneAreaStyle)();
  return (
    <div className={[styles.dropzone_banner, bannerStyle].join(" ")}>
      <Typography variant="h6">{bannerText}</Typography>
      <IconButton color="primary" className={styles.dropzone_iconBtn} onClick={openFileDialog}>
        <Icon className={styles.dropzone_icon}>{uploadIcon}</Icon>
      </IconButton>
    </div>
  );
};
```

### Props

```js
const defultProps = {
  BannerComponent: PropTypes.oneOfType([PropTypes.element, PropTypes.func]),
  PreviewComponent: PropTypes.oneOfType([PropTypes.element, PropTypes.func]),
  bannerStyle: PropTypes.string,
  bannerText: PropTypes.string,
  getProcessedData: PropTypes.func,
  inside: PropTypes.bool,
  limit: PropTypes.number,
  onChange: PropTypes.func,
  preview: PropTypes.bool,
  snackBarMessage: PropTypes.object,
  type: PropTypes.string,
  uploadIcon: PropTypes.string,
  wrapperProps: PropTypes.object,
  wrapperStyle: PropTypes.string
};
```

<br/>
<hr/>
<br/>

## DropzoneArea Usage

```jsx
import React, { Component } from "react";

import { DropzoneCard } from "@devloops/material-ui-dropzone";

const DropzoneCardExample = () => {
  return <DropzoneCard BannerComponent={DefaultBannerComponent /* or Default Comp*/} />;
};

const DefaultBannerComponent = ({ uploadIcon, bannerStyle, openFileDialog, bannerText }) => {
  const styles = makeStyles(DropzoneAreaStyle)();
  return (
    <div className={[styles.dropzone_banner, bannerStyle].join(" ")}>
      <Typography variant="h6">{bannerText}</Typography>
      <IconButton color="primary" className={styles.dropzone_iconBtn} onClick={openFileDialog}>
        <Icon className={styles.dropzone_icon}>{uploadIcon}</Icon>
      </IconButton>
    </div>
  );
};
```

<br/>

### Props

```js
const defultProps = {
  BannerComponent: PropTypes.oneOfType([PropTypes.element, PropTypes.func]),
  bannerStyle: PropTypes.string,
  bannerText: PropTypes.string,
  getProcessedData: PropTypes.func,
  onChange: PropTypes.func,
  snackBarMessage: PropTypes.object,
  type: PropTypes.string,
  uploadIcon: PropTypes.string,
  wrapperProps: PropTypes.object,
  wrapperStyle: PropTypes.string
};
```

<br/>
<hr/>
<br/>

## DropzoneArea Usage

```jsx
import React, { Component } from "react";

import { FileButton } from "@devloops/material-ui-dropzone";

const FileButtonExample = () => {
  return <FileButton {...anyProps} />;
};
```

### Props

```js
const defultProps = {
  ButtonComponent: PropTypes.oneOfType([PropTypes.element, PropTypes.func, PropTypes.elementType]),
  fabIcon: PropTypes.string,
  fabProps: PropTypes.object,
  onChange: PropTypes.func,
  onError: PropTypes.func,
  fabText: PropTypes.string,
  inputProps: PropTypes.object
};
```

## Todo List

- [x] Snackbar Support
- [x] Localize
- [ ] More Examples

## License

MIT © [meftunca](https://github.com/material-ui-dropzone)

---

This hook is created using [create-react-hook](https://github.com/hermanya/create-react-hook).
