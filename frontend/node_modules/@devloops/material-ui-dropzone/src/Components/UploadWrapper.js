import React, { useRef, useEffect, useState, Fragment } from "react";
import { Paper, makeStyles } from "@material-ui/core";
import { DropzoneAreaStyle } from "../utils/styles";
import { fileReader } from "./../utils/helper";
import PropTypes from "prop-types";
import SnacbarHelper, { snackBarHelperMessage } from "../utils/SnacbarHelper";
let func = () => {};
const UploadWrapper = ({
  onChange = func,
  type = "image",
  wrapperStyle = "",
  getProcessedData = func,
  wrapperProps = {},
  inside = true,
  preview = true,
  limit = 1,
  fileSizeLimit = 1024 * 1024,
  BannerComponent,
  snackBarMessage = snackBarHelperMessage,
  render: Children = Fragment
}) => {
  const [state, setState] = useState({ files: [], imgUrl: [] });
  const [snack, setSnack] = useState({ variant: "info", message: "", visible: false });
  const ref = useRef(null);
  const inputRef = useRef(null);
  const styles = makeStyles(DropzoneAreaStyle)();
  useEffect(() => {
    "drag dragstart dragend dragover dragenter dragleave".split(" ").map(i =>
      ref.current.addEventListener(i, function(e) {
        e.preventDefault();
        e.stopPropagation();
      })
    );
    "dragover dragenter".split(" ").map(i =>
      ref.current.addEventListener(i, e => {
        if (
          e.dataTransfer.items[0].type.split("/")[0] !== type ||
          e.dataTransfer.items.length + state.files.length > limit
        ) {
          ref.current.classList.add(styles["dropzone_is_dragover_danger"]);
        } else {
          ref.current.classList.add(styles["dropzone_is_dragover"]);
        }
      })
    );
    "dragleave dragend".split(" ").map(i =>
      ref.current.addEventListener(i, e => {
        ref.current.classList.remove(styles["dropzone_is_dragover"], styles["dropzone_is_dragover_danger"]);
      })
    );
    ref.current.onClick = () => {};
  }, []);
  const onDrop = async e => {
    e.preventDefault();
    e.stopPropagation();
    ref.current.classList.remove(styles["dropzone_is_dragover"], styles["dropzone_is_dragover_danger"]);
    let droppedFiles = e.target.files || e.dataTransfer.files,
      { imgUrl, files } = state;
    if (droppedFiles.length + state.files.length > limit) {
      setSnack({ ...snack, variant: "warning", message: snackBarMessage["limitedFile"], visible: true });
      return;
    }
    for (let file of droppedFiles) {
      if (file.type.split("/")[0] !== type) {
        ref.current.classList.add(styles["dropzone_is_dragover_danger"]);
        setSnack({
          ...snack,
          variant: "error",
          message: snackBarMessage["typeError"],
          visible: true
        });

        return false;
      } else if (fileSizeLimit < file.size) {
        ref.current.classList.add(styles["dropzone_is_dragover_danger"]);
        setSnack({
          ...snack,
          variant: "error",
          message: snackBarMessage["sizeError"],
          visible: true
        });

        return false;
      }
      files.push(file);
      imgUrl.push({ src: await fileReader(file), file });
    }
    setSnack({
      ...snack,
      variant: "success",
      message: snackBarMessage["success"],
      visible: true
    });
    setState({ ...state, files, imgUrl });
    getProcessedData(imgUrl);
    onChange(state);
  };
  const onDelete = index => {
    state.imgUrl = state.imgUrl.filter((i, k) => k !== index);
    state.files = state.files.filter((i, k) => k !== index);
    setState({ ...state });
    onChange(state);
    setSnack({
      ...snack,
      variant: "info",
      message: snackBarMessage["delete"],
      visible: true
    });
  };
  const openDialog = () => inputRef.current.click();

  return (
    <>
      <Paper
        elevation={2}
        {...wrapperProps}
        ref={ref}
        className={[styles.dropzone, wrapperStyle].join(" ")}
        onDrop={onDrop}
      >
        <BannerComponent openFileDialog={openDialog} size={state.files.length} />
        {preview === true && inside === true && <Children openFileDialog={openDialog} onDelete={onDelete} {...state} />}
      </Paper>
      {preview === true && inside === false && <Children openFileDialog={openDialog} onDelete={onDelete} {...state} />}
      <input type="file" hidden ref={inputRef} onChange={onDrop} />
      <SnacbarHelper
        variant={snack.variant}
        open={snack.visible}
        message={snack.message}
        onClose={() => setSnack({ ...snack, visible: false })}
      />
    </>
  );
};

UploadWrapper.propTypes = {
  getProcessedData: PropTypes.func,
  onChange: PropTypes.func,
  type: PropTypes.string,
  wrapperProps: PropTypes.object,
  wrapperStyle: PropTypes.string,
  limit: PropTypes.number
};

export default UploadWrapper;
