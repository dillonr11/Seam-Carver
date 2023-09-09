Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */

Our algorithm to find a vertical seam consists of creating an energy array
to represent the energies of each pixel, then an edgeTo and distTo arrays.
We then loop through the first row of the distTo array, and fill in the
values with their corresponding energies. We then loop through the rest of
the array, updating the edgeTo and distTo arrays. We then loop through the
bottom row to find the bottomMin, and minIndex. Then using the result, we
loop through the edgeTo array from the bottom up and update the verticalSeam[]
array. For horizontal seams, we transpose the image, then call
findVerticalSeam(), and then transpose the image back.

/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */

An image is more suitable if it has an extensive background, because the
background pixels will have relatively low energy, and many seams can be
removed from it without affected the content/structure of the original
picture. An image that would not work well would be a picture where there
are a lot of high energy pixels throughout, meaning there aren't many
pixels that can be removed without affecting the content/structure of the
original picture.


/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 2000
 multiplicative factor (for H) = 2

 H           time (seconds)      ratio       log ratio
------------------------------------------------------
300             0.229              -             -
600             0.376            1.642         0.715
1200            0.659            1.753         0.810
2400            1.333            2.023         1.016
4800            2.544            1.908         0.932


(keep H constant)
 H = 2000
 multiplicative factor (for W) = 2

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
300              0.242             -             -
600              0.412           1.702         0.767
1200             0.819           1.988         0.991
2400             1.560           1.905         0.930
4800             3.007           1.928         0.947



/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */


Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:


    ~ 1.76*10^-8 * W^0.91 * H^0.87


We found the formula for the run time by using the avg log ratio for both,
W and H.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

None.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

Nina's office hours.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

None.

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */

Dillon Remuck: Came up with different ideas for the methods and coded.
Christian Yaneshak: Came up with different ideas for the methods and coded.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

We both enjoyed the assignment.
