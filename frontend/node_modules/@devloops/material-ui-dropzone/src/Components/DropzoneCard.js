import React from "react";
import { makeStyles, IconButton, Icon, Typography, ButtonBase } from "@material-ui/core";
import { DropzoneCardStyle } from "./../utils/styles";
import PropTypes from "prop-types";
import UploadWrapper from "./UploadWrapper";
let func = () => {};
const DropzoneCard = ({
  onChange = func,
  type = "image",
  wrapperStyle = "",
  bannerStyle = "",
  BannerComponent,
  getProcessedData = func,
  bannerText = "Drag one or more files to this Drop Zone ...",
  uploadIcon = "add",
  wrapperProps = {},
  snackBarMessage
}) => {
  BannerComponent = BannerComponent || DefaultBannerComponent;
  const styles = makeStyles(DropzoneCardStyle)();
  return (
    <>
      <UploadWrapper
        {...{
          onChange,
          type,
          wrapperStyle: [styles.dropzone_banner, wrapperStyle].join(" "),
          getProcessedData,
          wrapperProps,
          limit: 1,
          snackBarMessage
        }}
        BannerComponent={({ ...props }) => <BannerComponent {...props} {...{ bannerStyle, bannerText, uploadIcon }} />}
        render={ImageFileWrapper}
      />
    </>
  );
};

DropzoneCard.propTypes = {
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

const ImageFileWrapper = ({ imgUrl: [i], onDelete, index: k = 0 }) => {
  const styles = makeStyles(DropzoneCardStyle)();
  console.log("i :", i);
  return (
    i !== undefined && (
      <>
        <div>
          <img src={i.src} className={styles.dropzone_overlay_img} />
        </div>
        <div className={styles.dropzone_topRight_btn}>
          <ButtonBase color="secondary" onClick={() => onDelete(k)}>
            <Icon>close</Icon>
          </ButtonBase>
        </div>
      </>
    )
  );
};

const DefaultBannerComponent = ({ uploadIcon, bannerStyle, openFileDialog, bannerText, size, ...rest }) => {
  const styles = makeStyles(DropzoneCardStyle)();
  return (
    size === 0 && (
      <div className={[styles.dropzone_banner, bannerStyle].join(" ")}>
        <IconButton color="default" onClick={openFileDialog}>
          <Icon className={styles.dropzone_icon}>{uploadIcon}</Icon>
        </IconButton>
        <Typography variant="subtitle1" align="center">
          {bannerText}
        </Typography>
      </div>
    )
  );
};
export default DropzoneCard;
