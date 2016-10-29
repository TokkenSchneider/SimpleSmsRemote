package tranquvis.simplesmsremote.CommandManagement;

import android.content.Context;

import tranquvis.simplesmsremote.Data.ControlModuleUserData;
import tranquvis.simplesmsremote.Data.DataManager;
import tranquvis.simplesmsremote.Data.LogEntry;
import tranquvis.simplesmsremote.Sms.MyMessage;

/**
 * Created by Andi on 08.10.2016.
 */

public class CommandExec
{
    private CommandInstance commandInstance;
    private Context context;

    public CommandExec(CommandInstance commandInstance, Context context) {
        this.commandInstance = commandInstance;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public CommandInstance getCommandInstance() {
        return commandInstance;
    }

    public CommandExecResult execute(MyMessage controlSms)
    {
        return null;
        /*

        else if (command == BATTERY_LEVEL_GET)
        {
            float batteryLevel = BatteryUtils.GetBatteryLevel(context);
            result.setCustomResultMessage(context.getResources().getString(
                    R.string.result_msg_battery_level, batteryLevel*100));
            result.setForceSendingResultSmsMessage(true);
        }
        else if (command == BATTERY_IS_CHARGING)
        {
            boolean isBatteryCharging = BatteryUtils.IsBatteryCharging(context);
            result.setCustomResultMessage(context.getString(
                    isBatteryCharging ? R.string.result_msg_battery_is_charging_true
                            : R.string.result_msg_battery_is_charging_false));
            result.setForceSendingResultSmsMessage(true);
        }
        else if (command == LOCATION_GET)
        {
            Location location = LocationUtils.GetLocation(context, 4000);
            if(location == null)
                throw new Exception("Location Request timed out");
            result.setCustomResultMessage(context.getString(
                    R.string.result_msg_location_coordinates,
                    location.getLatitude(), location.getLongitude()));
            result.setForceSendingResultSmsMessage(true);
        }
        else if (command == AUDIO_SET_VOLUME)
        {
            String audioTypeStr = commandInstance.getParam(PARAM_AUDIO_TYPE);
            String volumeStr = commandInstance.getParam(PARAM_AUDIO_VOLUME);

            AudioUtils.AudioType audioType = AudioUtils.AudioType.GetFromName(audioTypeStr);

            if(volumeStr.charAt(volumeStr.length() - 1) == '%')
            {
                float volumePercentage =
                        Float.parseFloat(volumeStr.substring(0, volumeStr.length() - 1));
                AudioUtils.SetVolumePercentage(context, volumePercentage, audioType);
            }
            else
            {
                int volumeIndex;
                switch (volumeStr)
                {
                    case "vibrate":
                        if(audioType != AudioUtils.AudioType.RING)
                            throw new Exception("vibrate is only possible for type ring");
                        volumeIndex = AudioUtils.VOLUME_INDEX_RING_VIBRATE;
                        break;
                    case "silent":
                        if(audioType != AudioUtils.AudioType.RING)
                            throw new Exception("vibrate is only possible for type ring");
                        volumeIndex = AudioUtils.VOLUME_INDEX_RING_SILENT;
                        break;
                    default:
                        volumeIndex = Integer.parseInt(volumeStr);
                        break;
                }

                AudioUtils.SetVolumeIndex(context, volumeIndex, audioType);
            }
        }
        else if (command == AUDIO_GET_VOLUME)
        {
            String audioTypeStr = commandInstance.getParam(PARAM_AUDIO_TYPE);
            AudioUtils.AudioType audioType = AudioUtils.AudioType.GetFromName(audioTypeStr);

            int volumeIndex = AudioUtils.GetVolumeIndex(context, audioType);
            String volumeStr = String.valueOf(volumeIndex);

            if(volumeIndex == AudioUtils.VOLUME_INDEX_RING_VIBRATE)
                volumeStr = "vibrate";
            else if(volumeIndex == AudioUtils.VOLUME_INDEX_RING_SILENT)
                volumeStr = "silent";

            result.setCustomResultMessage(context.getString(
                    R.string.result_msg_audio_volume, audioTypeStr, volumeStr));
            result.setForceSendingResultSmsMessage(true);
        }
        else if (command == AUDIO_GET_VOLUME_PERCENTAGE)
        {
            String audioTypeStr = commandInstance.getParam(PARAM_AUDIO_TYPE);
            AudioUtils.AudioType audioType = AudioUtils.AudioType.GetFromName(audioTypeStr);

            float volumePercentage = AudioUtils.GetVolumePercentage(context, audioType);

            result.setCustomResultMessage(context.getString(
                    R.string.result_msg_audio_volume_percentage, audioTypeStr,
                    volumePercentage));
            result.setForceSendingResultSmsMessage(true);
        }
        else if (command == DISPLAY_SET_BRIGHTNESS)
        {
            String brightnessStr = commandInstance.getParam(PARAM_BRIGHTNESS);

            float brightnessPercentage;
            if(brightnessStr.equals("auto"))
            {
                //set brightness mode to auto
                DisplayUtils.SetBrightnessMode(context, DisplayUtils.BrightnessMode.AUTO);
            }
            else
            {
                //set brightness percentage
                // regardless of whether a percentage character is given
                if (brightnessStr.endsWith("%"))
                {
                    brightnessPercentage = Float.parseFloat(brightnessStr.substring(0,
                            brightnessStr.length() - 1));
                }
                else
                {
                    brightnessPercentage = Float.parseFloat(brightnessStr);
                }
                DisplayUtils.SetBrightness(context, brightnessPercentage);
            }
        }
        else if (command == DISPLAY_GET_BRIGHTNESS)
        {
            float brightnessPercentage = DisplayUtils.GetBrightness(context);
            DisplayUtils.BrightnessMode brightnessMode =
                    DisplayUtils.GetBrightnessMode(context);
            String brightnessModeStr = brightnessMode == DisplayUtils.BrightnessMode.AUTO
                    ? "auto" : "manual";

            result.setCustomResultMessage(context.getString(
                    R.string.result_msg_display_brightness_percentage, brightnessPercentage,
                    brightnessModeStr));
            result.setForceSendingResultSmsMessage(true);
        }
        else if(command == DISPLAY_SET_OFF_TIMEOUT)
        {
            String timeoutStr =
                    commandInstance.getParam(Command.PARAM_DISPLAY_OFF_TIMEOUT);

            int timeoutStrLength = timeoutStr.length();
            String timeoutValueStr;
            int timeoutMilliseconds;
            if(timeoutStr.endsWith("ms"))
            {
                timeoutValueStr = timeoutStr.substring(0, timeoutStrLength - 2);
                timeoutMilliseconds = (int)Float.parseFloat(timeoutValueStr);
            }
            else if(timeoutStr.endsWith("s"))
            {
                timeoutValueStr = timeoutStr.substring(0, timeoutStrLength - 1);
                timeoutMilliseconds = (int)(Float.parseFloat(timeoutValueStr) * 1000);
            }
            else if(timeoutStr.endsWith("min"))
            {
                timeoutValueStr = timeoutStr.substring(0, timeoutStrLength - 3);
                timeoutMilliseconds = (int)(Float.parseFloat(timeoutValueStr) * 60000);
            }
            else
            {
               throw new Exception("no unit detected");
            }

            DisplayUtils.SetScreenOffTimeout(context, timeoutMilliseconds);
        }
        else if (command == DISPLAY_GET_OFF_TIMEOUT)
        {
            float screenOffTimeout = DisplayUtils.GetScreenOffTimeout(context);

            String timeoutStr;
            if(screenOffTimeout < 900)
                timeoutStr = String.format("%.0fms", screenOffTimeout);
            else if(screenOffTimeout < 60000)
                timeoutStr = String.format("%ds", Math.round(screenOffTimeout / 1000f));
            else
                timeoutStr = String.format("%.1fmin", screenOffTimeout / 60000f);

            result.setCustomResultMessage(context.getString(
                    R.string.result_msg_display_off_timeout, timeoutStr));
            result.setForceSendingResultSmsMessage(true);
        }
        else if (command == DISPLAY_TURN_OFF)
        {
            DisplayUtils.TurnScreenOff(context);
            Thread.sleep(2000); // otherwise the notification forces the screen to turn on again
        }
        else if (command == TAKE_PICTURE)
        {
            CameraModuleSettingsData moduleSettings = (CameraModuleSettingsData)
                    command.getModule().getUserData().getSettings();
            if(moduleSettings == null || moduleSettings.getDefaultCameraId() == null)
            {
                throw new Exception("Default camera not set.");
            }

            CameraUtils.MyCameraInfo cameraInfo = CameraUtils.GetCamera(context,
                    moduleSettings.getDefaultCameraId(), null);
            if(cameraInfo == null)
                throw new Exception("Default camera not found on device.");

            CaptureSettings captureSettings
                    = moduleSettings.getCaptureSettingsByCameraId(cameraInfo.getId());
            if(captureSettings == null)
                captureSettings = cameraInfo.getDefaultCaptureSettings();

            CameraUtils.TakePicture(context, cameraInfo, captureSettings);
        }
        else if (command == TAKE_PICTURE_WITH_OPTIONS)
        {
            //region retrieve given capture settings
            String cameraOptionsStr =
                    commandInstance.getParam(PARAM_TAKE_PICTURE_SETTINGS);
            String[] cameraOptionsStrList = cameraOptionsStr.split("\\s*,\\s*");

            String cameraId = null;
            CameraUtils.LensFacing lensFacing = null;
            CaptureSettings.FlashlightMode flashMode = null;
            Boolean autofocus = null;
            for (String optionStr : cameraOptionsStrList)
            {
                optionStr = optionStr.trim();
                CameraOptionsHelper.TakePictureOptions option =
                        CameraOptionsHelper.TakePictureOptions.FromOption(optionStr);
                if(option == null)
                    continue;
                switch (option)
                {
                    case CAMERA_INDEX:
                        cameraId = (String) option.getValue(optionStr);
                        break;
                    case FRONT_CAMERA:
                    case BACK_CAMERA:
                        lensFacing = (CameraUtils.LensFacing) option.getValue(optionStr);
                        break;
                    case FLASH_ON:
                    case FLASH_OFF:
                    case FLASH_AUTO:
                        flashMode = (CaptureSettings.FlashlightMode)
                                option.getValue(optionStr);
                        break;
                    case AUTOFOCUS_ON:
                    case AUTOFOCUS_OFF:
                        autofocus = (boolean) option.getValue(optionStr);
                        break;
                    default:
                        throw new NotImplementedException("Option not implemented.");
                }
            }
            //endregion

            CameraModuleSettingsData moduleSettings = (CameraModuleSettingsData)
                    command.getModule().getUserData().getSettings();

            //region get corresponding camera
            CameraUtils.MyCameraInfo cameraInfo;

            if(cameraId != null)
            {
                cameraInfo = CameraUtils.GetCamera(context, cameraId, null);
            }
            else if(lensFacing != null)
            {
                cameraInfo = CameraUtils.GetCamera(context, null, lensFacing);
            }
            else if(moduleSettings != null && moduleSettings.getDefaultCameraId() != null)
            {
                cameraInfo = CameraUtils.GetCamera(context,
                        moduleSettings.getDefaultCameraId(), null);
            }
            else
            {
                throw new Exception("Default camera not set.");
            }

            if(cameraInfo == null)
                throw new Exception("Default camera not found on device.");
            //endregion

            //region create capture settings
            CaptureSettings captureSettings = moduleSettings.getCaptureSettingsByCameraId(
                    cameraInfo.getId()).clone();
            if(captureSettings == null)
                captureSettings = cameraInfo.getDefaultCaptureSettings();

            if(flashMode != null)
                captureSettings.setFlashlight(flashMode);
            if(autofocus != null)
                captureSettings.setAutofocus(autofocus);
            //endregion

            CameraUtils.TakePicture(context, cameraInfo, captureSettings);

        }
        */
    }
}