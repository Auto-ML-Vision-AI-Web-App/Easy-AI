import React from "react";
import PropTypes from "prop-types";
import { IconButton, Icon, SnackbarContent, makeStyles, colors, Snackbar } from "@material-ui/core";
const { amber, green } = colors;

const variantIcon = {
  success: ({ ...props }) => <Icon {...props}>check_circle</Icon>,
  warning: ({ ...props }) => <Icon {...props}>warning</Icon>,
  error: ({ ...props }) => <Icon {...props}>error</Icon>,
  info: ({ ...props }) => <Icon {...props}>info</Icon>
};
const CloseIcon = ({ ...props }) => <Icon {...props}>close</Icon>;
const styles = makeStyles(theme => ({
  success: {
    backgroundColor: green[600]
  },
  error: {
    backgroundColor: theme.palette.error.dark
  },
  info: {
    backgroundColor: theme.palette.primary.main
  },
  warning: {
    backgroundColor: amber[700]
  },
  icon: {
    fontSize: 20
  },
  iconVariant: {
    opacity: 0.9,
    marginRight: theme.spacing(1)
  },
  message: {
    display: "flex",
    alignItems: "center",
    textTransform: "capitalize"
  }
}));

const SnacbarHelper = ({ className = "", open = false, message = "", onClose, variant = "info", ...other }) => {
  const classes = styles();
  const Icon = variantIcon[variant];
  return (
    <Snackbar open={open}>
      <SnackbarContent
        className={[classes[variant], className].join(" ")}
        aria-describedby="client-snackbar"
        message={
          <span id="client-snackbar" className={classes.message}>
            <Icon className={[classes.icon, classes.iconVariant].join(" ")} />
            {message}
          </span>
        }
        action={[
          <IconButton key="close" aria-label="close" color="inherit" onClick={onClose}>
            <CloseIcon className={classes.icon} />
          </IconButton>
        ]}
        {...other}
      />
    </Snackbar>
  );
};

SnacbarHelper.propTypes = {
  className: PropTypes.string,
  message: PropTypes.string,
  onClose: PropTypes.func,
  variant: PropTypes.oneOf(["error", "info", "success", "warning"]).isRequired
};
const snackBarHelperMessage = {
  limitedFile: "You have reached the file upload limit",
  typeError: "wrong file type",
  success: "file(s) added",
  sizeError: "Too large file",
  delete: "file(s) deleted"
};
export { snackBarHelperMessage };
export default SnacbarHelper;
