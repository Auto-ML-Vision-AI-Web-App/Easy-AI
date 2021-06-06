import React, { useState } from "react";
import { Paper, makeStyles, Grid, IconButton, Icon, Typography, Fade } from "@material-ui/core";
import { DropzoneAreaStyle } from "./../utils/styles";
import { sizeConverter, mimeType } from "./../utils/helper";
import PropTypes from "prop-types";
import UploadWrapper from "./UploadWrapper";
let func = () => {};
const DropzoneArea = ({
  onChange = func,
  type = "image",
  wrapperStyle = "",
  bannerStyle = "",
  BannerComponent,
  getProcessedData = func,
  inside = true,
  preview = true,
  PreviewComponent,
  bannerText = "Drag one or more files to this Drop Zone ...",
  uploadIcon = "cloud_upload",
  wrapperProps = {},
  limit = 1,
  snackBarMessage
}) => {
  BannerComponent = BannerComponent || DefaultBannerComponent;
  return (
    <>
      <UploadWrapper
        {...{
          onChange,
          type,
          wrapperStyle,
          getProcessedData,
          wrapperProps,
          limit,
          inside,
          preview,
          snackBarMessage
        }}
        BannerComponent={({ ...props }) => <BannerComponent {...props} {...{ bannerStyle, bannerText, uploadIcon }} />}
        render={PreviewComponent || DefaultPreviewComponent}
      />
    </>
  );
};

DropzoneArea.propTypes = {
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

const ImageFileWrapper = ({ item: i, onDelete, index: k }) => {
  const styles = makeStyles(DropzoneAreaStyle)();
  return (
    <div className={styles.relative}>
      <img src={i.src} className={styles.fullWidth} />
      <div className={styles.dropzone_overlay}>
        <Typography variant="caption" color="error">
          {sizeConverter(i.file.size)}
        </Typography>
        <IconButton color="secondary" onClick={() => onDelete(k)}>
          <Icon>delete</Icon>
        </IconButton>
      </div>
    </div>
  );
};
const DefaultPreviewComponent = ({ imgUrl, onDelete }) => (
  <Grid container spacing={3}>
    {imgUrl.map((i, k) => (
      <Grid item xs={3} md={2} key={k}>
        <Fade in={true} timeout={{ enter: (k + 1) * 500, exit: (k + 1) * 500 }}>
          {i.file.type.split("/")[0] === "image" ? (
            <ImageFileWrapper item={i} onDelete={onDelete} index={k} />
          ) : (
            <OtherFileWrapper item={i} onDelete={onDelete} index={k} />
          )}
        </Fade>
      </Grid>
    ))}
  </Grid>
);
const OtherFileWrapper = ({ item: i, onDelete, index: k }) => {
  const styles = makeStyles(DropzoneAreaStyle)();
  return (
    <Paper className={[styles.dropzone_banner, styles.paddingArea].join(" ")}>
      <Icon fontSize="large">{mimeType()[i.file.type.split("/")[0]]}</Icon>
      <br />
      <div className={styles.dropzone_banner}>
        <Typography variant="caption" color="error">
          {sizeConverter(i.file.size)}
        </Typography>
        <IconButton color="secondary" onClick={() => onDelete(k)}>
          <Icon>delete</Icon>
        </IconButton>
      </div>
    </Paper>
  );
};
const DefaultBannerComponent = ({ uploadIcon, bannerStyle, openFileDialog, bannerText, ...rest }) => {
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
export default DropzoneArea;
