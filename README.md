Twin Peaks Glyph
====================

About the Project
--------------
This Glyph Matrix toy contains two animated patterns inspired by Twin Peaks, switchable with a long press:

1. **Zigzag Pattern** - The iconic Red Room floor pattern with animated zigzag chevrons continuously flowing upward across the 25x25 LED matrix.

2. **Owl Symbol Glitch** - Twin Peaks owl symbol (three connected diamonds) with glitchy flicker effect, mimicking an unstable electrical connection. The pattern randomly flickers on and off like the eerie lights in the Red Room.

**Features:**
- Two distinct visual animations in one toy
- Switch between patterns using long-press on Glyph Button
- Smooth animations optimized for Nothing Phone (3) Glyph Matrix
- Pure visual experience - no user interaction required

After going through the `Setup` stage in this document, the project can be run on your Nothing device.

## Demo

![Twin Peaks Glyph Demo](https://github.com/user-attachments/assets/fed92d26-50bc-4ee9-ab99-299427d3edc6)



The project already contains the necessary libraries (GlyphMatrix SDK) and clean production-ready code structure. For SDK documentation, please reference the [**GlyphMatrix Development Kit**](https://github.com/KenFeng04/GlyphMatrix-Development-Kit).

This project is written in Kotlin and utilizes a useful Kotlin wrapper `GlyphMatrixService.kt`, which wraps around the original SDK.

Requirements
--------------
Android Studio, Kotlin, compatible device with Glyph Matrix

Setup
-----------------------
**1.** Prepare your Nothing device and connect it to the computer for development

**2.** Clone this project or download this repository as a ZIP and uncompress it to your local directory.

**3.** Open a new windows in Android studio and hit file on the menu bar, select open.

<p align="center">
<img src="images/open.png" alt="Open Project" style="max-height: 300px;">
</p>

**4.** Select the directory where you have cloned the repository or the unzipped folder and click `Open`

<p align="center">
<img src="images/select.png" alt="Select Project" style="max-height: 300px;">
</p>

**5.** Once the Gradle files have been synced and your phone is connected properly, you should see your device name shown at the top and a play button. Click the play button to install this example project.

<p align="center">
<img src="images/run.png" alt="Run Project" style="max-height: 300px;">
</p>

Running the Glyph Toy
------------
When the project is installed on your Nothing device, the Twin Peaks toy needs to be activated before it can be used.

> **Tip:** Use `Long-press` on the Glyph Button to switch between Zigzag and Owl Symbol patterns!

<table>
<tr>
<td width="60%" valign="top">

**1.** Open the `Glyph Interface` from your device settings.

**2.** Tap on the first button on the right menu to access toy management.

</td>
<td width="40%" align="center">
<img src="images/toy_carousoul.png" alt="Disabled Toys" style="max-height: 300px;">
</td>
</tr>

<tr>
<td width="60%" valign="top">

**3.** Use the handle bars to drag a toy from `Disabled` to `Active` state.

</td>
<td width="40%" align="center">
<img src="images/toy_disable.png" alt="Moving Toys" style="max-height: 300px;">
</td>
</tr>

<tr>
<td width="60%" valign="top">

**4.** The Twin Peaks toy should now be in the `Active` state. The Zigzag pattern will start by default. Use `Long-press` on the Glyph Button to switch to the Owl Symbol glitch animation.

</td>
<td width="40%" align="center">
<img src="images/toy_active.png" alt="Active Toys" style="max-height: 300px;">
</td>
</tr>
</table>

## Credits
**Built by:** Mia & 31 of NOLA SYSTEMS  
**Inspired by:** Twin Peaks TV series by David Lynch  
**SDK:** Nothing GlyphMatrix Developer Kit

---

*Completely useless but damn fine glyph* ☕
